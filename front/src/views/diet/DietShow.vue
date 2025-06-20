<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>饮食录入</span>
        <el-button type="primary" @click="dialogVisible=true">录入饮食</el-button>
      </div>
    </template>

    <!-- 条件筛选区域：时间和餐次 -->
    <el-form :model="filterForm" class="filter-form" size="small" inline>
      <el-form-item label="时间" style="width: 200px">
        <el-date-picker v-model="filterForm.dietDate" type="date" placeholder="选择饮食日期" style="width: 100%" />
      </el-form-item>

      <el-form-item label="餐次" style="width: 200px">
        <el-select 
        v-model="filterForm.mealType" 
        placeholder="请选择餐次" 
        clearable 
        @clear="filterForm.mealType = ''"
        >
          <el-option label="早餐" value="早餐" />
          <el-option label="午餐" value="午餐" />
          <el-option label="晚餐" value="晚餐" />
          <el-option label="加餐" value="加餐" />
        </el-select>
      </el-form-item>

      <!-- 搜索和重置 -->
      <el-form-item style="width: 200px">
        <el-button type="primary" @click="getDietFoods">查询</el-button>
        <el-button @click="resetFilter">重置</el-button>
      </el-form-item>

    </el-form>

    <!-- 餐次的展示 -->
    <el-table :data="meals">
      <el-table-column label="序号" width="60" type="index" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="dietDate" label="饮食日期" />
      <el-table-column prop="mealType" label="餐次" />

      <!-- 合并 foodList 和 quantityList -->
      <el-table-column label="食物与数量">
        <template #default="{ row }">
          <div v-for="(food, index) in row.foodList" :key="index">
            {{ food }} {{ row.quantityList[index] }} g
          </div>
        </template>
      </el-table-column>

      <!-- 展示图片 -->
      <el-table-column label="图片">
        <template #default="{ row }">
          <div style="display: flex; gap: 4px;">
            <ImgShow
              v-for="(img, index) in row.imagePathList"
              :key="index"
              :imgUrl="img"
              :width="100"
            />
          </div>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
      layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
      @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    <!-- 上传饮食记录 -->
    <el-dialog v-model="dialogVisible" title="饮食录入" width="40%" :rules="rules" @close="clearMealModel">
      <el-form :model="mealModel" :rules="rules" ref="mealForm" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="mealModel.realName" readonly />
        </el-form-item>

        <el-form-item label="日期" prop="dietDate">
          <el-date-picker v-model="mealModel.dietDate" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-form-item>

        <el-form-item label="餐次" prop="mealType">
          <el-select v-model="mealModel.mealType" placeholder="请选择餐次" style="width: 100%;">
            <el-option label="早餐" value="早餐" />
            <el-option label="午餐" value="午餐" />
            <el-option label="晚餐" value="晚餐" />
            <el-option label="加餐" value="加餐" />
          </el-select>
        </el-form-item>

        <el-form-item label="食物列表" required>
          <el-table :data="foodTable" style="width: 100%">
            <el-table-column label="食物名称">
              <template #default="scope">
                <el-input v-model="scope.row.foodName" placeholder="请输入食物名称" />
              </template>
            </el-table-column>

            <el-table-column label="数量（g）" width="180">
              <template #default="scope">
                <el-input-number
                  v-model="scope.row.quantity"
                  :min="0.1"
                  :step="0.1"
                  placeholder="请输入数量"
                  style="width: 100%"
                />
              </template>
            </el-table-column>

            <el-table-column label="操作" width="108">
              <template #default="scope">
                <el-button type="primary" @click="addFoodRow" circle >
                  <el-icon><Plus /></el-icon>
                </el-button>
                <el-button type="danger" @click="removeFoodRow(scope.$index)" circle>
                  <el-icon><Minus /></el-icon>
                </el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="uploadDietFoods">上传</el-button>
      </template>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import dayjs from 'dayjs'
import { Plus, Minus } from '@element-plus/icons-vue'
import ImgShow from '@/views/img/ImgShow.vue'

const filterForm = ref({
  dietDate: '',
  mealType: ''
})

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getDietFoods();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getDietFoods();
}

const meals = ref([])

// 搜索
import { getDietFoodsService, uploadDietFoodsService } from '@/api/Diet.js'
const getDietFoods = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    mealType: filterForm.value.mealType
  }
  if(filterForm.value.dietDate){
    params.dietDate = dayjs(filterForm.value.dietDate).format('YYYY-MM-DD')
  }
  let result = await getDietFoodsService(params)
  if(result.data){
    total.value = result.data.total;
    meals.value = result.data.items;
  }
}
getDietFoods()

const resetFilter = () => {
  filterForm.value = {
    dietDate: '',
    mealType: ''
  }
  getDietFoods();
}


import { useUserInfoStore } from '@/stores/userInfo'
const userInfoStore = useUserInfoStore();

const dialogVisible = ref(false);
// 饮食录入的数据模型
const mealModel = ref({
  realName: userInfoStore.getUserInfo().realName,
  dietDate: '',
  mealType: '',
  foodList: [],
  quantityList: [],
  imagePathList: []
})

const rules = {
  realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  dietDate: [{ required: true, message: '请选择日期', trigger: 'change' }],
  mealType: [{ required: true, message: '请选择餐次', trigger: 'change' }],
  foodList: [{ required: true, message: '请输入食物名称', trigger: 'blur' }],
  quantityList: [{ required: true, message: '请输入数量', trigger: 'change' }],
  imagePathList: [{ required: true, message: '请输入图片路径', trigger: 'blur' }]
}

// 上传表格中的食物行
const foodTable = ref([
  { foodName: '', quantity: 0 }
])

const addFoodRow = () => {
  foodTable.value.push({ foodName: '', quantity: 0 })
}

const removeFoodRow = (index) => {
  foodTable.value.splice(index, 1)
}

import { ElMessage } from 'element-plus'
const uploadDietFoods = async()=>{
  const form = mealModel.value;
  form.dietDate = dayjs(form.dietDate).format('YYYY-MM-DD');
  form.foodList = foodTable.value.map(item => item.foodName);
  form.quantityList = foodTable.value.map(item => item.quantity);

  let result = await uploadDietFoodsService(form);
  dialogVisible.value = false;
  ElMessage.success('饮食记录上传成功');
  getDietFoods();
}

const clearMealModel = ()=>{
  mealModel.value = {
    realName: '',
    dietDate: '',
    mealType: '',
    foodList: [],
    quantityList: [],
    imagePathList: []
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
</style>
