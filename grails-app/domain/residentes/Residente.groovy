package residentes

class Residente {

    String numeroControl
    String nombreAlumno
    Empresa empresa
    String objetivo
    String correoElectronico
    boolean autorizado
    Asesor asesor


    static constraints = {
        numeroControl blank: false, unique: true
        nombreAlumno blank: false, nullable:false
        empresa blank: false, nullable:true
        objetivo blank: true, nullable: true
        correoElectronico email:true, blank: false, nullable: true
        autorizado nullable: false, blank: false
        asesor blank: false

    }

    String toString(){
        return "No Control: ${numeroControl} "
    }
}
