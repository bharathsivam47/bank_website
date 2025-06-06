function formvalidate()
{
    if(fullnamejs() && usernamejs() && aadharjs() && emailjs() && phonejs() && fatherjs() && addressjs())
        return true;
    else
      return false;
}
function regexval(id,errid,regex)
{

    let valu=(document.getElementById(id).value).trim();
    const err=document.getElementById(errid);
    if(!(regex.test(valu)))
    {
        err.style.display="block";
        return false;
    }
    else{
        err.style.display="none";
        return true;
    }
}
function fullnamejs()
{
    const reg=/^[A-Za-z]{3,24}$/;
    return regexval("first","fullerr",reg);
}
function usernamejs()
{
    const regex=/^(?=.*\d)[A-Za-z0-9]{6,10}$/;
    return regexval("username","usererr",regex);
}
function aadharjs()
{
    const regex=/^[2-9][0-9]{11}$/;
    return regexval("Aadhaar-Number","aaderr",regex);
}
function emailjs()
{
    const regex=/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regexval("email","emailerr",regex);
}
function phonejs()
{
    const phoneRegex = /^[6-9]\d{9}$/;
    return regexval("phone","phoneerr",phoneRegex);
}
function fatherjs()
{
    const regex=/^[A-Za-z]{3,24}$/;
    return regexval("Fathers","fathererr",regex);
}
function addressjs()
{
    const regex = /^(?=.{10,100}$)\d{1,4}\/\d{1,4}\s[\w\s\-']+(?:\sstreet)?\s[\w\s\-']+$/i;

    return regexval("address","addresserr",regex);
}