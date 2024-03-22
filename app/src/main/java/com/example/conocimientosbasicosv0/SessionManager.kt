import android.content.Context
import com.example.conocimientosbasicosv0.Cuenta
import com.google.gson.Gson

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val gson = Gson()

    companion object {
        const val USER_ACCOUNT_KEY = "user_account"
    }

    fun saveLoggedInAccount(cuenta: Cuenta) {
        val cuentaJson = gson.toJson(cuenta)
        editor.putString(USER_ACCOUNT_KEY, cuentaJson)
        editor.apply()
    }

    fun getLoggedInAccount(): Cuenta? {
        val cuentaJson = prefs.getString(USER_ACCOUNT_KEY, null)
        return if (cuentaJson != null) gson.fromJson(cuentaJson, Cuenta::class.java) else null
    }

    fun clearSession() {
        editor.remove(USER_ACCOUNT_KEY)
        editor.apply()
    }
}
