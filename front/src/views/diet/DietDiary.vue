<template>
  <div>
    <el-table :data="tableData" style="width: 60%" border>
      <el-table-column label="姓名">
        <template #default="scope">
          <el-input v-model="scope.row.name" placeholder="请输入姓名" />
        </template>
      </el-table-column>

      <el-table-column label="年龄">
        <template #default="scope">
          <el-input v-model="scope.row.age" type="number" placeholder="请输入年龄" />
        </template>
      </el-table-column>

      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button type="danger" icon="Delete" @click="deleteRow(scope.$index)" />
          <el-button type="primary" icon="Plus" @click="addRow" style="margin-top: 10px;">
            添加一行
          </el-button>
        </template>
      </el-table-column>
    </el-table>


  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Delete, Plus } from '@element-plus/icons-vue'

const tableData = ref([
  { name: '', age: '' }
])

const addRow = () => {
  tableData.value.push({ name: '', age: '' })
}

const deleteRow = (index) => {
  tableData.value.splice(index, 1)
}
</script>
