FROM node:20-alpine AS build
WORKDIR /app
COPY package.json tsconfig.json ./
COPY vendor ./vendor
RUN npm install
COPY src ./src
RUN npm run build

FROM node:20-alpine
WORKDIR /app
COPY --from=build /app/dist ./dist
COPY --from=build /app/node_modules ./node_modules
COPY config ./config
COPY templates ./templates
COPY prompts ./prompts
ENV NODE_ENV=production
EXPOSE 3000
USER node
CMD ["node", "dist/server.js"]
