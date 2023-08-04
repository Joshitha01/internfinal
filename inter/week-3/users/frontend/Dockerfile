FROM node:16

RUN mkdir -p /usr/frontend

WORKDIR /usr/frontend

COPY package*.json ./

RUN npm install -g npm@9.6.2

RUN npm install 

COPY . .

EXPOSE 3000

CMD ["npm", "start"]