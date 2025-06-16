import './assets/main.scss'

import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 导入中文包
import zhCn from 'element-plus/dist/locale/zh-cn.js'
// pinia
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

const app = createApp(App)
const pinia = createPinia()
// 使用持久化插件
const persist = createPersistedState()

app.use(router)
app.use(ElementPlus,  { locale: zhCn })
pinia.use(persist)
app.use(pinia)

app.mount('#app')
