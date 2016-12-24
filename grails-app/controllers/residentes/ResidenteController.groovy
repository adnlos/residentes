package residentes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResidenteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Residente.list(params), model:[residenteCount: Residente.count()]
    }

    def show(Residente residente) {
        respond residente
    }

    def create() {
        respond new Residente(params)
    }

    @Transactional
    def save(Residente residente) {
        if (residente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (residente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond residente.errors, view:'create'
            return
        }

        residente.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'residente.label', default: 'Residente'), residente.id])
                redirect residente
            }
            '*' { respond residente, [status: CREATED] }
        }
    }

    def edit(Residente residente) {
        respond residente
    }

    @Transactional
    def update(Residente residente) {
        if (residente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (residente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond residente.errors, view:'edit'
            return
        }

        residente.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'residente.label', default: 'Residente'), residente.id])
                redirect residente
            }
            '*'{ respond residente, [status: OK] }
        }
    }

    @Transactional
    def delete(Residente residente) {

        if (residente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        residente.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'residente.label', default: 'Residente'), residente.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'residente.label', default: 'Residente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
