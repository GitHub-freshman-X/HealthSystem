<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>所有账号</span>
      </div>
    </template>

    <!-- 展示 -->
    <el-table :data="users">
      <el-table-column label="用户ID" width="100" prop="userId" />
      <el-table-column label="用户名" width="100" prop="username" />
      <el-table-column label="用户真名" width="100" prop="realName" />
      <el-table-column label="性别" width="100" prop="gender" />
      <el-table-column label="出生日期" width="100" prop="birthDate" />
      <el-table-column label="身高" width="100" prop="height" />
      <el-table-column label="头像" prop="avatarUrl" />

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

    <!-- 修改 -->
    <el-dialog v-model="dialogVisible" title="修改数据" width="40%" @close="clearUserModel">
      <el-form :model="userModel" :rules="rules" label-width="100px">
          <el-form-item label="用户名">
            <el-input v-model="userModel.username" disabled />
          </el-form-item>

          <!-- 姓名 -->
          <el-form-item label="姓名" prop="realName">
            <el-input v-model="userModel.realName" placeholder="请输入真实姓名" />
          </el-form-item>

          <!-- 性别 -->
          <el-form-item label="性别" prop="gender">
            <el-select v-model="userModel.gender" placeholder="请选择性别">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
          </el-form-item>

          <!-- 出生日期 -->
          <el-form-item label="出生日期" prop="birthDate">
            <el-date-picker
              v-model="userModel.birthDate"
              type="date"
              placeholder="选择出生日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>

          <!-- 身高 -->
          <el-form-item label="身高 (cm)" prop="height">
            <el-input v-model="userModel.height" placeholder="请输入身高" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="update">提交修改</el-button>
          </el-form-item>

      </el-form>
    </el-dialog>

  </el-card>
</template>

<script setup>

import { ref } from 'vue';

const users = ref([]);

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getUsers();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getUsers();
}

import { 
  getAllUsersService,
  updateUserService,
  deleteUserService
} from '@/api/Admin.js';
const getUsers = async()=>{
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }

  let result = await getAllUsersService(params);
  if(result.data){
    total.value = result.data.total;
    users.value = result.data.items;
  }
}
getUsers()

const dialogVisible = ref(false);
const userModel = ref({
  username: '',
  realName: '',
  gender: '',
  birthDate: '',
  height: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  gender : [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  birthDate: [
    { required: true, message: '请选择出生日期', trigger: 'change' }
  ],
  height: [
    { required: true, message: '请输入身高', trigger: 'blur' }
  ]
}

const clearUserModel = ()=>{
  userModel.value = {
    username: '',
    realName: '',
    gender: '',
    birthDate: '',
    height: ''
  }
}

const handleEdit = (row)=>{
  userModel.value = { ...row };
  dialogVisible.value = true;
}

import { ElMessage, ElMessageBox } from 'element-plus';
const handleDelete = async(row)=>{
  ElMessageBox.confirm(
  '确定要删除该用户吗？',
  '温馨提示',
  {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }
)
  .then(async () => {
    let result = await deleteUserService(row.userId);
    ElMessage.success('删除成功');
    getUsers();
  })
  .catch(() => {
    ElMessage.info('取消删除');
  })
}

const update = async()=>{
  let result = await updateUserService(userModel.value)
  ElMessage.success('用户信息更新成功');
  dialogVisible.value = false;
  getUsers();
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
