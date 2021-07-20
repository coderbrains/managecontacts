console.log("hello world...");

function toggle()
{

    if($(".sidebar").is(":visible")){

        //true blockk is here.

        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");


    }else{

        //false block is here.

        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");

    }


}