//一次导入多个变量
//会将指定模块中的所有导出封装成一个对象
import * as a from "./export.js"

console.log(a.age)
console.log(new a.Teacher())