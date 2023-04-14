export {}

import axios from 'axios'
import type { CreateAxiosDefaults, AxiosInstance } from 'axios'
import { HttpStatusCode } from 'axios'
import type { App } from 'vue';

declare interface HttpPlugin {
  install(app: App): void;
  statusCode: typeof HttpStatusCode
}

function createHttp(options: CreateAxiosDefaults) : HttpPlugin {
  const http: HttpPlugin = {
    install(app: App) {
      app.config.globalProperties.$http = axios.create(options)
    },
    statusCode: HttpStatusCode
  }

  return http
}

declare module 'vue' {
  interface ComponentCustomProperties {
    $http: AxiosInstance
  }
}

console.log(import.meta.env.VITE_API_URL, import.meta.env)
const http = createHttp({
  baseURL: import.meta.env.VITE_API_URL
})

export default http