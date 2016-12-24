package residentes

class Asesor {

    String nombre
    String area
    String correoElectronico


    static constraints = {
        nombre nullable: false, blank:false
        area nullable: true,    : true
        correoElectronico email:true, blank:false
    }


    String toString(){
        return "Asesor: ${nombre}"
    }

}
