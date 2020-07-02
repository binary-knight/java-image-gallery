/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.au.cc.gallery;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {


    public static void main(String[] args) throws SQLException {
	String portString = System.getenv("JETTY_PORT");
	if (portString == null || portString.equals(""))
	port(5000);
	else
		port(Integer.parseInt(portString));
	
/* This is the main admin GET request */

	get("/admin", (req, res) -> {
		Map<String, Object> model = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList();
		ArrayList<Map> listMap = new ArrayList();
		UserAdmin ua = new UserAdmin();
		list = ua.listUsers();
		
		list.forEach((i) -> {
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("username", i);
					listMap.add(temp);
				  });
		model.put("users", listMap);
		return new HandlebarsTemplateEngine().render(new ModelAndView(model, "admin.hbs"));
		});

	
/* Actions */

        get("/admin/createuser", (req, res) -> {
                Map<String, Object> model = new HashMap<String, Object>();
                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "create-user.hbs"));
                });


	get("/admin/edituser", (req, res) -> {
                Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", req.queryParams("name"));
                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "edit.hbs"));
                });
 
     	get("/admin/deleteuser", (req, res) -> {
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("name", req.queryParams("name"));
                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "delete.hbs"));
                });

     /* Confirmation Pages */

     	get("/admin/edit-confirm", (req, res) -> {
                UserAdmin ua = new UserAdmin();
                Map<String, Object> model = new HashMap<String, Object>();

		if (req.queryParams("name").equals("")) {
			return new HandlebarsTemplateEngine().render(new ModelAndView(model, "input-error.hbs"));
                }

		boolean success = ua.editUser(req.queryParams("name"), req.queryParams("password"), req.queryParams("fullName"));
		model.put("name", req.queryParams("name"));

		return new HandlebarsTemplateEngine().render(new ModelAndView(model, "edit-confirm.hbs"));
                });

        get("/admin/delete-confirm", (req, res) -> {
                UserAdmin ua = new UserAdmin();
		Map<String, Object> model = new HashMap<String, Object>();

		if (req.queryParams("name").equals("")) {
                        return new HandlebarsTemplateEngine().render(new ModelAndView(model, "error.hbs"));
                }

		ua.deleteUser(req.queryParams("name"));
		model.put("name", req.queryParams("name"));

		return new HandlebarsTemplateEngine().render(new ModelAndView(model, "delete-confirm.hbs"));
                });


        get("/admin/create-confirm", (req, res) -> {
	
		UserAdmin ua = new UserAdmin();
		Map<String, Object> model = new HashMap<String, Object>();
	
                if (req.queryParams("name").equals("")) {
                	return new HandlebarsTemplateEngine().render(new ModelAndView(model, "error.hbs"));
                }

		ua.addUser(req.queryParams("name"), req.queryParams("password"), req.queryParams("fullName"));
		model.put("name", req.queryParams("name"));

                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "create-confirm.hbs"));
                });

/* Error on Input */

        get("/admin/error", (req, res) -> {
                Map<String, Object> model = new HashMap<String, Object>();
                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "error.hbs"));
				});

	}

}