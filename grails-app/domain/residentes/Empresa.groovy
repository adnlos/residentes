package residentes

class Empresa {

    String nombre
    String direccion
    String giro
    String correoElectronico
    String telefono


    static constraints = {
        nombre blank:false, nullable:false
        direccion blank: true, nullable: true
        giro blank: true, nullable: true
        correoElectronico blank: false, nullable: false
        telefono blank: true, nullable: true
    }


    String toString(){
        return "Empresa: ${nombre}"
    }

}
