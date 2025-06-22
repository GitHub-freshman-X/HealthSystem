<template>
  <el-card header="膳食记录">
    <el-tabs v-model="activeTab">
      <el-tab-pane 
        v-for="meal in meals" 
        :key="meal.type" 
        :label="meal.type + ' ' + meal.time" 
        :name="meal.type">
        
        <el-timeline>
          <el-timeline-item 
            v-for="(food, idx) in meal.items" 
            :key="idx"
            >
            <div class="food-item">
              <span class="name">{{ food.name }}</span>
              <div class="badges">
                <el-tag size="small" type="info">{{ food.calories }}千卡</el-tag>
                <el-tag size="small" type="success">P:{{ food.protein }}g</el-tag>
                <el-tag size="small">C:{{ food.carbs }}g</el-tag>
                <el-tag size="small" type="warning">F:{{ food.fat }}g</el-tag>
              </div>
            </div>
          </el-timeline-item>
        </el-timeline>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  meals: {
    type: Array,
    required: true
  }
})

const activeTab = ref(props.meals[0]?.type || '') // 默认选中第一个标签页
</script>

