package se.fransman.tg.shared.datalayer.localsettings



import com.russhwolf.settings.Settings
import com.russhwolf.settings.string
import se.fransman.tg.shared.viewmodel.screens.Level1Navigation

class MySettings (s : Settings) {
  // here we define all our local settings properties,
  // by using the MultiplatformSettings library delegated properties
  var savedLevel1URI by s.string(defaultValue = Level1Navigation.Dashboard.screenIdentifier.URI)
}