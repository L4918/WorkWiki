import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';


createApp(App).use(store).use(router).use(Antd).mount('#app')
//Vue 的基本逻辑就是 初始执行 main.ts 将内容也App.vue渲染到index.html,完成页面显示