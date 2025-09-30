<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-input v-model:value="param.name" placeholder="名称">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="level1"
        :loading="loading"
        :pagination="false"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar /">
        </template>
        <template v-slot:action="{ text,record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复·确认删除？"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record.id)"
            >
              <a-button danger >
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      title="文档表单"
      v-model:open="open"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="doc" :label-col="{ span: 6 }">
      <a-form-item label="名称">
        <a-input v-model:value="doc.name" />
      </a-form-item>
      <a-form-item label="父文档">
        <a-select
            ref="select"
            v-model:value="doc.parent"
        >
          <a-select-option value="0">无</a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="doc.id === c.id">
            {{c.name}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="doc.sort" />
      </a-form-item>

    </a-form>

  </a-modal>
</template>


<script lang="ts">
  import { defineComponent, onMounted,ref} from "vue";
  import axios from "axios";
  import { message } from "ant-design-vue";
  import {Tool} from "@/util/tool";

  export default defineComponent({
    name: 'AdminDoc',
    setup(){
      const param = ref();
      param.value = {};
      const docs = ref();
      const loading = ref(false)

      const columns = [
        {
          title: '名称',
          dataIndex:'name'
        },
        {
          title: '父文档',
          key:'parent',
          dataIndex: 'parent'
        },
        {
          title: '顺序',
          dataIndex: 'sort'
        },
        {
          title: 'Action',
          key: 'action',
          slots: { customRender: 'action' }
        }
      ];

      // 一级文档树,children属性就是二级文档
      // [{
      //   id:"",
      //   name:"",
      //   children:[{
      //     id:"",
      //     name:"",
      //   }]
      // }]
      const level1 = ref(); //一级文档树，children属性就是二级文档
      /**
       * 数据查询
       */
      const handleQuery = () => {
        loading.value = true;
        axios.get("/doc/all").then((response) => {
          loading.value = false;
          const data = response.data;
          if (data.success){
            docs.value = data.content;
            console.log("原始数组:",docs.value);

            level1.value = [];
            level1.value = Tool.array2Tree(docs.value,0)
            console.log("树形结构:",level1);
          }else {
            message.error(data.message);
          }
        });
      };


      // ---------- 表单 ----------
      const doc = ref({})
      const open = ref<boolean>(false);
      const modalLoading = ref<boolean>(false);
      const handleModalOk = () => {
        modalLoading.value = true;

        axios.post("/doc/save",doc.value).then((response) =>{
          modalLoading.value = false;

          const data = response.data; //data = commonResp
          if (data.success){
            open.value = false;

            //重新加载列表
            handleQuery();
          } else {
            message.error(data.message);
          }
        });

      };



      /**
       * 编辑
       */
      const edit = (record: any) => {
        open.value = true;
        doc.value = Tool.copy(record)
      };

      /**
       * 新增
       */
      const add = () => {
        open.value = true;
        doc.value = {};
      };

      const handleDelete = (id: number) => {
        axios.delete("/doc/delete/" + id).then((response) => {
          const data = response.data; // data = commonResp
          if (data.success){
            //重新加载列表
            handleQuery();
          }
        })
      };

      onMounted(() =>{
        handleQuery();
      });

      return{
        param,
        //docs,
        level1,
        columns,
        loading,
        handleQuery,

        edit,
        add,

        doc,
        open,
        modalLoading,
        handleModalOk,

        handleDelete
      }

    }
  });

</script>

<style scoped>
  img{
    width: 50px;
    height: 50px;
  }
</style>