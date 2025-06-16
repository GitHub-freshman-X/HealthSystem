import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      '/api': { // 获取路径中包含/api的请求
        target: 'http://localhost:8080', 
        changeOrigin: true, // 是否跨域
        rewrite: (path) => path.replace(/^\/api/, '') // 重写路径，将/api去掉
      }
    }
  }
})
