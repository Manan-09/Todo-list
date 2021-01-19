package com.manan.tracker.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manan.tracker.Dao.todorepo;

import com.manan.tracker.Entity.Todo;

@Controller
public class todoController 
{
	@Autowired
	public todorepo therepo;
	
	@RequestMapping("/")
	public String alltasks(Model model)
	{
		List<Todo> todos=therepo.findAll();
		model.addAttribute("todos",todos);
		return "todo-list";
	}
	
	@RequestMapping("/update")
	public String showUpdate(@RequestParam("taskId") int theId,Model theModel) 
	{

		Optional<Todo> result = therepo.findById(theId);
		
		Todo thetodo = null;
		
		if (result.isPresent()) {
			thetodo = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find task id - " + theId);
		}
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("todo", thetodo);
		
		// send over to our form
		return "task-form";			
	}
	
	@GetMapping("/add")
	public String Add(Model theModel) {
		
		// create model attribute to bind form data
		Todo thetodo = new Todo();
		
		theModel.addAttribute("todo", thetodo);
		
		return "task-form";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("taskId") int theId) 
	{

		therepo.deleteById(theId);
		
		// send over to our form
		return "redirect:/";			
	}
	
	@RequestMapping("/clear")
	public String clear(Model model)
	{
		therepo.deleteAll();
		return "redirect:/";		
	}
	@RequestMapping("/done")
	public String done(@RequestParam("taskId") int theId) 
	{

		
		Optional<Todo> result = therepo.findById(theId);
		
		Todo thetodo = null;
		
		if (result.isPresent()) {
			thetodo = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find task id - " + theId);
		}
		
		thetodo.setStatus(true);
		therepo.save(thetodo);
		
		List<Todo> todos = therepo.findAll();
		boolean flag=false;
		for(Todo todo : todos)
		{
			if(todo.isStatus()==false)
				flag=true;
		}
		// send over to our form
		if(flag)
			return "redirect:/";
		else
			return "redirect:/clear";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute("todo")Todo todo) 
	{
		todo.setStatus(false);
		therepo.save(todo);
		
		// send over to our form
		return "redirect:/";			
	}
}
