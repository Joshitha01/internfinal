import express from 'express'
//import {PORT} from './config/variables'
import cors from 'cors'
import http from 'http'

const PORT = 3002;
const app = express()
const server = http.createServer(app)
const mqtt = require('mqtt');
const Docker = require('dockerode');
const docker = new Docker();
const stats = require('docker-stats');
//const healthCheck = require('dockerode-healthcheck');
app.use(cors());
app.get('/containers', async (req, res) => {
  try {
    const containers = await getRunningContainers();
    res.json(containers);
  } catch (error) {
    console.error('Error fetching container information:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.post('/containers/stop/:id', async (req, res) => {
  const containerId = req.params.id;
  try {
    const container = await docker.getContainer(containerId);
    await container.stop();
    res.json({ message: 'Container stopped successfully' });
  } catch (error) {
    console.error('Error stopping container:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.post('/containers/start/:id', async (req, res) => {
  const containerId = req.params.id;
  try {
    const container = await docker.getContainer(containerId);
    await container.start();
    res.json({ message: 'Container started successfully' });
  } catch (error) {
    console.error('Error starting container:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});
async function getRunningContainers() {
  const containers = await docker.listContainers({ all: true });
  return containers.map((container) => ({
    id: container.Id,
    name: container.Names[0],
    image: container.Image,
    ports: container.Ports.map((port) => `${port.PublicPort}:${port.PrivatePort}`),
    running: container.State === 'running',
  }));
}


app.get('/containers/details/:id', async (req, res) => {
  const containerId = req.params.id;
  try {
    const container = await docker.getContainer(containerId);
    const containerStats = await getContainerStats(containerId);

    const details = {
      id: containerId,
      name: container.name,
      image: container.image,
      ports: container.ports,
      running: container.running,
      cpu: containerStats.cpu_stats.cpu_usage.total_usage,
      memory: containerStats.memory_stats.usage,
     // health: await getContainerHealth(container),
    };

    res.json(details);
  } catch (error) {
    console.error('Error fetching container details:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

function getContainerStats(containerId) {
  return new Promise((resolve, reject) => {
    docker.getContainer(containerId).stats(
      {
        stream: false,
      },
      (err, stats) => {
        if (err) {
          reject(err);
          return;
        }
        resolve(stats);
      }
    );
  });
}
function getContainerHealth(container) {
  return new Promise((resolve, reject) => {
    healthCheck(container, (err, health) => {
      if (err) {
        reject(err);
        return;
      }
      resolve(health);
    });
  });
}



app.listen(PORT, () => {
  console.log(`Server up and running on port ${PORT}`);
})

// server.listen(PORT, () => {
//   console.log(`Server up and running on port ${PORT}`);
// })