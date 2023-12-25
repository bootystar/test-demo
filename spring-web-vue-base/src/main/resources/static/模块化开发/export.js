//单独导出某个变量

export let age = 10;

//一次导出多个变量
let a = 10;
let b = 20;
let c = 30;
export {a, b, c}


//导出方法
export function say(){
    console.log("hello")
}

//导出类
export  class Teacher{
    teach(){
        console.log("teach-method")
    }
}
