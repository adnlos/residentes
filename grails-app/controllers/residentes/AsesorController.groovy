package residentes

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AsesorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Asesor.list(params), model:[asesorCount: Asesor.count()]
    }

    def show(Asesor asesor) {
        respond asesor
    }

    def create() {
        respond new Asesor(params)
    }

    @Transactional
    def save(Asesor asesor) {
        if (asesor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (asesor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond asesor.errors, view:'create'
            return
        }

        asesor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'asesor.label', default: 'Asesor'), asesor.id])
                redirect asesor
            }
            '*' { respond asesor, [status: CREATED] }
        }
    }

    def edit(Asesor asesor) {
        respond asesor
    }

    @Transactional
    def update(Asesor asesor) {
        if (asesor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (asesor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond asesor.errors, view:'edit'
            return
        }

        asesor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'asesor.label', default: 'Asesor'), asesor.id])
                redirect asesor
            }
            '*'{ respond asesor, [status: OK] }
        }
    }

    @Transactional
    def delete(Asesor asesor) {

        if (asesor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        asesor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'asesor.label', default: 'Asesor'), asesor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'asesor.label', default: 'Asesor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
