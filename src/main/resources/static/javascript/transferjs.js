function depositvalidate()
{
    if(accountno() && amount() && confirm())
        return true;
    else
      return false;

}
function regval(id,error,regex)
{
   let values=document.getElementById(id).value;
   let errors=document.getElementById(error);
   if(!(regex.test(values)))
   {
    errors.style.display='block';
    return false;
   }
   else
   {
    errors.style.display='none';
    return true;
   }
}
function accountno()
{
    const regex=/^\d{7,10}$/;
   return regval('Accountnumber','accerror',regex);

}
function validate()
{
    const regex=/^(10|[1-9][0-9]{1,4}|10000)$/;
    return regval('amount1','amounterr',regex);
}
function confirm()
{
let pass=document.getElementById("Accountnumber").value;
let pass1=document.getElementById("conAccountnumber").value;
const error=document.getElementById("conaccerror");
   if(pass!==pass1)
   {
     error.style.display="block";
      return false;
   }
   else{
     error.style.display="none";
     return true;
   }
}