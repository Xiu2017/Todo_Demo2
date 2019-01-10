//
//  1、在历史数据加载完成后显示主体
//  2、根据存储记录中已选中的状态展示代办事项
//  3、输入框自动聚焦
//  4、待办事项不记录纯空格和空字符
//  5、输入不限字数、列表无上限
//  6、可输入和展示css html js vue等代码而不被执行
//  7、可单独删除某一项
//  8、可单独标记或取消标记某一项为"Completed"状态
//  9、双击待办任务可进行编辑，为空或者纯空格会被删除
//  10、可将全部待办事项状态置为"Active"或"Completed"
//  11、可统计状态为非"Completed"的数量
//  12、可清除所有状态为"Completed"的待办事项
//

/**
 * 页面加载完成时
 */
window.onload = function(){
    toggleStatus();
};

/**
 * 改变全选按钮的状态
 */
function toggleStatus(){
}

/**
 * 监听按键事件
 */
document.onkeyup = function (event) {
    let e = event || window.event || arguments.callee.caller.arguments[0];
    let input = document.activeElement;
    if (e && e.keyCode == 13 && input && input.className == "edit") {
        doneEdit(input);
    } else if (e && e.keyCode == 27) {
        escEdit();
    }
};

/**
 * 添加待办事项
 */
function addTodo() {
    let input = document.getElementsByClassName("new-todo")[0];
    let content = input.value;
    let reg = new RegExp(" ", "g"); //创建正则RegExp对象
    let length = content.replace(reg, "").length;
    return length > 0;
}

/**
 * 编辑代办事项
 */
function editTodo(element) {
    let li = element.parentNode.parentNode;
    li.classList.add("editing");

    let edit = li.getElementsByClassName("edit")[0];
    edit.focus();
}

/**
 * 编辑完成
 * @param element
 */
function doneEdit(element) {
    //解决和ESC冲突
    if(escBlur){
        escBlur = false;
        return;
    }
    let content = element.value;
    let id = element.parentNode.getElementsByClassName("todo-id")[0].value;
    formSubmit(id, content);
}

/**
 * 取消编辑
 */
var escBlur = false;  //解决事件冲突
function escEdit() {
    escBlur = true;
    let list = document.getElementsByClassName("editing");
    for (let li of list) {
        let content = li.getElementsByClassName("content")[0].innerText;
        let input = li.getElementsByClassName("edit")[0];
        input.value = content;
        li.classList.remove("editing");
    }
}

/**
 * js动态创建form 提交表单
 */
function formSubmit(id, content) {
    if(content != null){
        let form = document.createElement("form");
        //一定要加入到body中！！
        document.body.appendChild(form);
        form.method = 'post';
        //创建隐藏表单
        let input1 = document.createElement("input");
        input1.setAttribute("name", "id");
        input1.setAttribute("type", "hidden");
        input1.setAttribute("value", id);
        form.appendChild(input1);

        //如果内容为空，则删除该记录
        let reg = new RegExp(" ", "g"); //创建正则RegExp对象
        let length = content.replace(reg, "").length;
        if(length > 0){
            let input2 = document.createElement("input");
            input2.setAttribute("name", "content");
            input2.setAttribute("type", "hidden");
            input2.setAttribute("value", content);
            form.appendChild(input2);
            form.action = 'TodoServlet?method=update&last='+method;
            form.submit();
        }else{
            form.action = "TodoServlet?method=delete&last="+method;
            form.submit();
        }
    }
}