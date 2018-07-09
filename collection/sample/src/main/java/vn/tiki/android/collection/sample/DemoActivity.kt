package vn.tiki.android.collection.sample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_demo.*
import vn.tiki.android.collection.OnlyAdapter

class DemoActivity : AppCompatActivity() {

  private lateinit var adapter: OnlyAdapter

  var count: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_demo)
    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    adapter = OnlyAdapter()
    recyclerView.adapter = adapter
    val viewModel = ViewModelProviders.of(this).get(DemoViewModel::class.java)
    menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    menuRecyclerView.adapter = OnlyAdapter()
      .apply {
        submitList(
          listOf(
            ButtonModel(
              text = "Add Text",
              onClick = {
                val textModel: TextModel = makeText(++count)
                viewModel.add(textModel)
              }),

            ButtonModel(
              text = "Add User",
              onClick = {
                val userModel: UserModel = makeUser(++count)
                viewModel.add(userModel)
              }),

            ButtonModel(
              text = "Remove",
              onClick = {
                viewModel.remove(1)
              }),

            ButtonModel(
              text = "Insert",
              onClick = {
                val userModel: UserModel = makeUser(++count)
                viewModel.insert(1, userModel)
              }),

            ButtonModel(
              text = "Update",
              onClick = {
                val userModel: UserModel = makeUser(++count)
                viewModel.update(1, userModel)
              })
          )
        )
      }
  }

  private fun makeUser(id: Int): UserModel {
    val name = "User $id"
    return UserModel(name) {
      println("$name is clicked")
    }
  }

  private fun makeText(id: Int): TextModel {
    return TextModel("Text $id")
  }

  override fun onDestroy() {
    super.onDestroy()
    println("DemoActivity.onDestroy")
    ExampleApplication.get(this).refWatcher.watch(this)
  }
}
