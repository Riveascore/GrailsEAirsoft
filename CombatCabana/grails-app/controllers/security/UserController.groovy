package security

import java.security.Security;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.dao.DataIntegrityViolationException

class UserController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def springSecurityService

	
	//To render things via ajax in modals
//	d ajaxify = ['signup', 'save']
	//Edit, create, delete, show, list
	//Only edit if User.id == springSecurityService.getCurrentUser().id
	/*
	 * Show -> edit if
	 * User.id == springSecurityService.getCurrentUser().id
	 * Else: show -> show
	 * 
	 * edit -> edit if
	 * User.id == springSecurityService.getCurrentUser().id
	 * Else: edit -> show
	 * 
	 *  list -> list
	 *  
	 *  create -> "current action with same params" 
	 *  if springSecurityService.getCurrentUser() != null
	 *  Else: create -> create
	 *  
	 *  delete -> "current action with same params" 
	 *  if params.user?id == springSecurityService.getCurrentUser().id
	 */
	//

	def index() {
		redirect(action: "list", params: params)
	}

	def list(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		//It appears that User.count has errors if there are 0 Users in the database?
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}



	def create() {
		[userInstance: new User(params)]
	}

	def signup() {
		[userInstance: new User(params)]
	}

	def save = {
		
		def userInstance = new User(params)
		userInstance.setEnabled(true)
		
		Boolean errorsExist
		
		if (!userInstance.save(flush: true)) {
			render(view: 'signup', model: [userInstance: userInstance, errorsExist: true])
			return
		}
		
		render(view: 'signup', model: [newUsername: userInstance.username, errorsExist: false])
		return
	}

	def show(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}

	def edit(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "list")
			return
		}

		[userInstance: userInstance]
	}

	def update(Long id, Long version) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "list")
			return
		}

		if (version != null) {
			if (userInstance.version > version) {
				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'user.label', default: 'User')] as Object[],
						"Another user has updated this User while you were editing")
				render(view: "edit", model: [userInstance: userInstance])
				return
			}
		}

		userInstance.properties = params

		if (!userInstance.save(flush: true)) {
			render(view: "edit", model: [userInstance: userInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'user.label', default: 'User'),
			userInstance.id
		])
		redirect(action: "show", id: userInstance.id)
	}

	def delete(Long id) {
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "list")
			return
		}

		try {
			userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'user.label', default: 'User'),
				id
			])
			redirect(action: "show", id: id)
		}
	}
}
