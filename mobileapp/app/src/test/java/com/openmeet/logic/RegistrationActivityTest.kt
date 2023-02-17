package com.openmeet.logic


import android.content.Context
import android.text.Editable
import android.widget.EditText
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.storage.DAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import java.sql.Timestamp
import java.util.concurrent.CountDownLatch


@RunWith(RobolectricTestRunner::class)
class RegistrationActivityTest {


    private lateinit var registrationActivity: RegistrationActivity
    private lateinit var cntx: Context

    private lateinit var nameField: TextInputLayout
    private lateinit var surnameField: TextInputLayout
    private lateinit var emailField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var confirmPasswordField: TextInputLayout
    private lateinit var birthdateField: TextInputLayout

    private var birthdateTime: Long = Timestamp.valueOf("2001-06-30 00:00:00").time
    private var registrationMail = "user@email.com"

    /*@get:Rule
    val activityRule = ActivityTestRule(RegistrationActivity::class.java)*/
    /*@get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()*/


    @Before
    fun setUp(){

        registrationActivity = Robolectric.buildActivity(RegistrationActivity::class.java).get()
        cntx = InstrumentationRegistry.getInstrumentation().targetContext
        //val scenario = launchActivity<RegistrationActivity>()

        nameField = mock(TextInputLayout::class.java)
        surnameField = mock(TextInputLayout::class.java)
        birthdateField = mock(TextInputLayout::class.java)
        emailField = mock(TextInputLayout::class.java)
        passwordField = mock(TextInputLayout::class.java)
        confirmPasswordField = mock(TextInputLayout::class.java)

        val nameEditText = mock(EditText::class.java)
        `when`(nameEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Pippo"))
        `when`(nameField.editText).thenReturn(nameEditText)

        val surnameEditText = mock(EditText::class.java)
        `when`(surnameEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Baudo"))
        `when`(surnameField.editText).thenReturn(surnameEditText)

    }


    @After
    fun tearDown(){
        println("TEST COMPLETED")
    }

    /*fun cleanUpDB(){
        Thread{
            assertTrue(MeeterProxyDAO(cntx).doDelete("${Meeter.MEETER_EMAIL} = '$registrationMail'"))
            println("DELETED $registrationMail FROM DATABASE")
        }.start()
    }*/

    /*
       Lunghezza Parte Locale
     */
    @Test
    fun checkNLPL() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("@email.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))


        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("s9ic37i02uv86rig2j0wuencvnj0tt5lwf4ailtdo11hbvn0xar76ihl7ozjlfv63@y.com"))

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
       Lunghezza Parte Dominio
     */
    @Test
    fun checkLNPD() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("user@"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))


        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("user@bjoykbzzp7i6ol4t1umjzqfbcz8ylucpu9mlem59oaim7barvrhfupn1i" +
                "210znv9u7eq5jsghxuyegilrmlg1uaowrymf4wlv8zpp2o3zq1t6hu825mb8zuma9bp9" +
                "hpho1phy279eba2q1ytkaxuaxdpi7ctv5ujylqn4gxkcyyf60tb6jl2kkwfuweftn322" +
                "01v0tunp81i4nxqind3ybc42xbxuzk5zug9kackddvhmyf6lsb5r4upxkh41i2y.com"))

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
       Presenza singola Chiocciola
     */
    @Test
    fun checkPC() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("useremail.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))


        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("u@ser@email.com"))

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
       Legalità Punto Parte Locale
     */
    @Test
    fun checkLPPL() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(".user@email.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))


        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("user@email.com."))

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
       Legalità Punto Parte Dominio
     */
    @Test
    fun checkLPPD() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("user@.email.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))


        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("user@email.com."))

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
      Non-Consecutività Punti
    */
    @Test
    fun checkNCP() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("us..er@email.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
     Legalità Caratteri Speciali Parte Locale
   */
    @Test
    fun checkLCPSL() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("userone漢@email.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

    }

    /*
     Legalità Caratteri Parte Dominio
   */
    @Test
    fun checkLCPD() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("userone@emai!l.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

    }

    /*
        Assenza Numeri Dominio Primo Livello
  */
    @Test
    fun checkANDPL() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("userone@emai0l.com"))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        assertTrue(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("userone@email.c0m"))
        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

    }

    /*
       Presenza Database
    */
    @Test
    fun checkPD() {

        val email = "iuiouiouio@gmail.com"


        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(email, cntx))
        }.start()
    }

    // PASSWORD
    /*
       Lunghezza
  */
    @Test
    fun checkLN() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("ciao"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("thispasswordisveryveryverylong"))
        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

    }

    /*
      Presenza Carattere Speciale
 */
    @Test
    fun checkPCS() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("password"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

    }

    /*
      Presenza Numero
 */
    @Test
    fun checkPN() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("password$"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
      Presenza Lettera Maiuscola
 */
    @Test
    fun checkLMAI() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("password$0"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
      Presenza Lettera Minuscola
 */
    @Test
    fun checkLMIN() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("PASSWORD$0"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }

    /*
     Password Identica
    */
    @Test
    fun checkPI() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password$0"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password123!"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter(emailField.editText?.text.toString(), cntx))
        }.start()

        assertFalse(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))
    }


    /*
        Do Registration
    */
    @Test
    fun checkRegistration() {

        val emailEditText = mock(EditText::class.java)
        `when`(emailEditText.text).thenReturn(Editable.Factory.getInstance().newEditable(registrationMail))
        `when`(emailField.editText).thenReturn(emailEditText)

        val passwordEditText = mock(EditText::class.java)
        `when`(passwordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password$0"))
        `when`(passwordField.editText).thenReturn(passwordEditText)

        val confirmPasswordEditText = mock(EditText::class.java)
        `when`(confirmPasswordEditText.text).thenReturn(Editable.Factory.getInstance().newEditable("Password$0"))
        `when`(confirmPasswordField.editText).thenReturn(confirmPasswordEditText)


        val meeter = Meeter()
        meeter.meeterName = nameField.editText?.text.toString()
        meeter.meeterSurname = surnameField.editText?.text.toString()
        meeter.email = emailField.editText?.text.toString()
        meeter.pwd = passwordField.editText?.text.toString()
        meeter.birthdate = java.sql.Date(birthdateTime)

        Thread {
            assertTrue(registrationActivity.verifyUniregisteredMeeter("yuri@yuribrandi.com", cntx))
        }.start()

       assertTrue(registrationActivity.checkForm(nameField, surnameField, birthdateTime, birthdateField, emailField, passwordField, confirmPasswordField, false))

        Thread {
            assertTrue(registrationActivity.doRegisterMeeter(meeter, cntx))
        }.start()
    }

}