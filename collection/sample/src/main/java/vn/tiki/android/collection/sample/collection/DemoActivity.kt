package vn.tiki.android.collection.sample.collection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_demo.*
import vn.tiki.android.collection.OnlyAdapter
import vn.tiki.android.collection.sample.ExampleApplication
import vn.tiki.android.collection.sample.R
import vn.tiki.android.collection.sample.viewholder.ButtonModel
import vn.tiki.android.collection.sample.viewholder.CheckBoxModel
import vn.tiki.android.collection.sample.viewholder.CollectionModel
import vn.tiki.android.collection.sample.viewholder.TextFieldModel
import vn.tiki.android.collection.sample.viewholder.TextModel
import vn.tiki.android.collection.sample.viewholder.UserModel

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
    viewModel.list.observe(this, Observer { adapter.submitList(it) })
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
              text = "Add CheckBox",
              onClick = {
                val checkBoxModel: CheckBoxModel = makeCheckBox(++count) { model ->
                  viewModel.update(model)
                }
                viewModel.add(checkBoxModel)
              }),

            ButtonModel(
              text = "Add TextField",
              onClick = {
                val textField: TextFieldModel = makeTextField("username") { text ->
                  viewModel.setUserName(text)
                }
                viewModel.add(textField)
              }),

            ButtonModel(
              text = "Add collection",
              onClick = {
                viewModel.add(CollectionModel)
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
    return UserModel(name)
  }

  private fun makeText(id: Int): TextModel {
    return TextModel("Text $id")
  }

  private fun makeCheckBox(id: Int, onClick: (CheckBoxModel) -> Unit): CheckBoxModel {
    return CheckBoxModel(id, false, "Check Box $id")
      .apply { this.onClick = onClick }
  }

  private fun makeTextField(id: String, onUsernameChanged: (String) -> Unit): TextFieldModel {
    return TextFieldModel(id, "")
      .apply { this.onUsernameChanged = onUsernameChanged }
  }

  override fun onDestroy() {
    super.onDestroy()
    println("DemoActivity.onDestroy")
    ExampleApplication.get(this).refWatcher.watch(this)
  }
}
