import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue';

const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app');
//Vue 的基本逻辑就是 初始执行 main.ts 将内容也App.vue渲染到index.html,完成页面显示

//全局使用图标
const icons: any = Icons;
for (const i in icons) {
    app.component(i,icons[i]);
}

console.log('环境:',process.env.NODE_ENV);
console.log('服务端:',process.env.VUE_APP_SERVER);