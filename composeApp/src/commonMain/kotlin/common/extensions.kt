package common


fun String.isPasswordValid() : Boolean{
    if(this.isBlank()){
        return false
    }
    if(this.length < 5){
        return false
    }
    if(this.none { char -> char.isDigit() || char.isUpperCase() || !char.isLetterOrDigit() }){
        return false
    }
    return true
}


fun String.isEmailValid() : Boolean{
    if(this.isBlank()){
        return false
    }
    if(this.length < 6){
        return false
    }
    if(this.none { char -> char == '@' }  || this.none { char -> char == '.' }){
        return false
    }
    return true
}