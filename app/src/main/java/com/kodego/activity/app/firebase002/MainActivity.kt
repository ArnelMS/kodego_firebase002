package com.kodego.activity.app.firebase002

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kodego.activity.app.firebase002.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dao = EmployeeDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSave.setOnClickListener(){
            dao.add(Employee(binding.etName.text.toString(),binding.etSalary.text.toString()))
            Toast.makeText(applicationContext,"Success", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoad.setOnClickListener(){
            view()
            Toast.makeText(applicationContext,"Loading...", Toast.LENGTH_SHORT).show()
        }
        binding.btnUpdate.setOnClickListener(){
            updateData()
            Toast.makeText(applicationContext,"Updating...", Toast.LENGTH_SHORT).show()
        }
        binding.btnDelete.setOnClickListener(){
            deleteData()
            Toast.makeText(applicationContext,"Deleting...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun deleteData() {
        dao.remove("-NLiVaOMcWtKMeE9eVpr")
    }

    private fun updateData() {
        var mapData = mutableMapOf<String,String>()
        mapData["name"] = "Update Name"
        mapData["salary"] = "Update Amount"
        dao.update("-NLiXB2onlF8XChvfVj_",mapData)
    }

    private fun view() {
        dao.get().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var employees: ArrayList<Employee> = ArrayList<Employee>()

                var dataFromDb = snapshot.children

                for(data in dataFromDb) {
                    // get id of object from DB
                    var id = data.key.toString()

                    var name =  data.child("name").value.toString()
                    var salary = data.child("salary").value.toString()

                    var employee = Employee(name, salary)
                    employees.add(employee)

                    Toast.makeText(applicationContext,"$id"+employees, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}