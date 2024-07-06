ARG NODE_VERSION=21.4.0
FROM node:${NODE_VERSION}-slim

LABEL maintainer="tu_email@example.com"
LABEL commit=$COMMIT_HASH
LABEL build_date=$BUILD_DATE

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]