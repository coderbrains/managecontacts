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


};

function search()
{
    
	
    var query = $('#search-input').val();

    if(query == '')
    {
        $('.search-result').hide();

    }else{
        console.log(query);

        let url = `http://localhost:6002/searchcontacts/${query}`;

        fetch(url).then((response) => {
            return response.json();
        }).then((data) => {

            let text = `<div class="list-group">`;

            data.forEach(element => {
                
                text += `<a href = '/user/viewcontact/${element.contactID}' class="list-group-item list-group-item-action">${element.name}</a>`;
            });


            text += `</div>`;

            $(".search-result").html(text);
            $(".search-result").show();
            
        })


        $('.search-result').show();
    }
	

}