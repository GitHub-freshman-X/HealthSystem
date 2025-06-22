<template>
  <el-card header="膳食记录">
    <el-tabs v-model="activeTab">
      <el-tab-pane 
        v-for="meal in meals" 
        :key="meal.type" 
        :label="meal.type" 
        :name="meal.type">
        
        <el-timeline>
          <el-timeline-item 
            v-for="(food, idx) in meal.items" 
            :key="idx">
            <div class="food-item">
              <div class="food-info">
                <div class="food-name">{{ food.name }}</div>
                <div class="food-tags">
                  <el-tag size="small" type="success">{{ food.quantity }} g</el-tag>
                  <el-tag size="small" type="warning">{{ food.calories }} kcal</el-tag>
                </div>
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

const activeTab = ref(props.meals[0]?.type || '') // 默认选中第一个
</script>

<style scoped>
.food-item {
  display: flex;
  align-items: center;
  gap: 12px;
}
.food-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #eee;
}
.food-info {
  display: flex;
  flex-direction: column;
}
.food-name {
  font-weight: bold;
  margin-bottom: 4px;
}
.food-tags {
  display: flex;
  gap: 8px;
}
</style>
