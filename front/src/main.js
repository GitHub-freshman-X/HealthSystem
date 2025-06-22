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

// 使用ECharts
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
// 引入图表类型
import { BarChart, LineChart } from 'echarts/charts'
// 引入渲染器
import { CanvasRenderer } from 'echarts/renderers'
// 引入组件
import { TitleComponent, TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'

// 注册所需内容
use([
  BarChart,
  LineChart,
  CanvasRenderer,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent
])

const app = createApp(App)
const pinia = createPinia()
// 使用持久化插件
const persist = createPersistedState()

// 全局注册 v-chart 组件
app.component('v-chart', VChart)

app.use(router)
app.use(ElementPlus,  { locale: zhCn })
pinia.use(persist)
app.use(pinia)

app.mount('#app')
