
function validate()
{
  const regex=/^\d{7,10}$/;
  const error=document.getElementById("accerr");
  console.log("account validate");
  let accno=document.getElementById("Accountnumber").value;
  if(!(regex.test(accno)))
  {
    error.style.display="block";
    return false;
  }
  else{
    error.style.display="none";
    return true;
  }
}
function confacc()
{
  let accno=document.getElementById("Accountnumber").value;
  let conaccno=document.getElementById("conAccountnumber").value;
  const error=document.getElementById("conaccerr");
  if(accno !==conaccno)
  {
    error.style.display="block";
    return false;
  }
  else{
  error.style.display="none";
      return true;
  }
}
function confirmacc()
{
  if(validate() && confacc())
  {
    const res=confirm("Are you Sure Want To Delete a Account ");
    if(res)
    return true;
    else
    return false;
  }
  else
     return false;
}