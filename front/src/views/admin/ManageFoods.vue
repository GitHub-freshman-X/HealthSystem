<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>所有食物</span>
        <el-button type="primary" @click="dialogVisible=true; title='添加食物'">添加食物</el-button>
      </div>
    </template>

    <!-- 展示 -->
    <el-table :data="foods">
      <el-table-column label="编号" width="100" prop="foodId" />
      <el-table-column label="食物名" width="200" prop="name" />
      <el-table-column label="图片">
        <template #default="{ row }">
          <div style="display: flex; gap: 4px;">
            <ImgShow :imgUrl="row.imagePath" :width="100" />
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <el-dialog v-model="dialogVisible" :title="title" @close="clearFoodModel">
      <el-form :model="foodModel" label-width="100px" >
        <el-form-item label="食物名">
          <el-input v-model="foodModel.name" />
        </el-form-item>

        <el-form-item>
          <el-upload 
            class="avatar-uploader" 
            :auto-upload="true" 
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers = "{ 'Authorization': tokenStore.getToken() }"
            :on-success="uploadSuccess"
          >
            <img v-if="foodModel.imagePath" :src="foodModel.imagePath" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="title=='添加食物' ? uploadFood() : updateFood()">上传</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import ImgShow from '@/views/img/ImgShow.vue'
import { Plus } from '@element-plus/icons-vue';

import { useTokenStore } from '@/stores/token';
const tokenStore = useTokenStore()

const foods = ref([]);
const title = ref('')

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getFoods();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getFoods();
}

import {
  getAllFoodsService,
  updateFoodService,
  deleteFoodService,
  uploadFoodService
}from '@/api/Admin.js'

const getFoods = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  let result = await getAllFoodsService(params)
  if(result.data){
    total.value = result.data.total
    foods.value = result.data.items
  }
}
getFoods()

const dialogVisible = ref(false)
const foodModel = ref({
  foodId: '',
  name: '',
  imagePath: ''
})

const handleEdit = (row)=>{
  title.value = '更新食物'
  foodModel.value = { ...row };
  dialogVisible.value = true;
}

import { ElMessage, ElMessageBox } from 'element-plus';
const handleDelete = async(row)=>{
  ElMessageBox.confirm(
  '确定要删除这种食物吗？',
  '温馨提示',
  {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }
)
  .then(async () => {
    let result = await deleteFoodService(row.foodId);
    ElMessage.success('删除成功');
    getFoods();
  })
  .catch(() => {
    ElMessage.info('取消删除');
  })
}

const uploadSuccess = (result)=>{
  foodModel.value.imagePath = result.data
}
const updateFood = async()=>{
  let result = await updateFoodService(foodModel.value)
  ElMessage.success('食物信息更新成功');
  dialogVisible.value = false;
  getFoods();
}
const uploadFood = async()=>{
  let result = await uploadFoodService(foodModel.value)
  ElMessage.success('食物上传成功');
  dialogVisible.value = false;
  getFoods();
}

const clearFoodModel = ()=>{
  foodModel.value = {
    foodId: '',
    name: '',
    imagePath: ''
  }
}

</script>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.filter-form {
  margin-bottom: 20px;
}

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
</style>
