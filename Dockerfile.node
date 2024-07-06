ARG NODE_VERSION
ARG COMMIT_HASH
ARG BUILD_DATE
ARG BRANCH_NAME

FROM node:${NODE_VERSION}-alpine

LABEL maintainer="tu_email@example.com"
LABEL build_date=${BUILD_DATE}
LABEL branch=${BRANCH_NAME}




WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]