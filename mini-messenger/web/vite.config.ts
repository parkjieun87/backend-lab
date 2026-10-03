import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// API 서버와 같은 출처로 보이게 해서 JSESSIONID 쿠키가 따라가도록 프록시한다.
const api = 'http://localhost:8080'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/auth': api,
      '/rooms': api,
      '/messages': api,
    },
  },
})
