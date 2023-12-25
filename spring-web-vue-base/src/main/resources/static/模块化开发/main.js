
//从其他模块导入文件并自定义命名(导入的是一个对象)
// import {a} from "./goods.js";
//使用变量
// console.log(a)



//从有多个导出的模块引入导入的文件(选择多个)
import {age,a,b,c} from "./export.js"
console.log(age)

//导入方法(输入方法名)
import {say} from "./export.js"
say()

//导入类
import {Teacher} from "./export.js"
let teacher=new Teacher()
teacher.teach()

//导入自定义变量名(多个模块冲突时使用)(导出的变量需要指定默认名)
import cc from "./goods.js";
import ccc from "./order.js";
console.log(cc)
console.log(ccc)

//导入多个可自定义名称的变量
import object from "./default.js"
console.log(object.g)