using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Windows;
using System.Windows.Resources;
using System.Xml.Linq;

using Newtonsoft.Json.Linq;

namespace WPCordovaClassLib.Cordova.Commands
{
  public class PreferencesPlugin : BaseCommand
  {
    private readonly string preferencesString;

    public PreferencesPlugin()
    {
      // This code is lifted from ConfigHandler.LoadAppPackageConfig because that
      // class doesn't expose all the preferences as a single object: you can
      // only query based on a known preference name.

      StreamResourceInfo streamInfo = Application.GetResourceStream(new Uri("config.xml", UriKind.Relative));

      if (streamInfo != null)
      {
        StreamReader sr = new StreamReader(streamInfo.Stream);
        XDocument document = XDocument.Parse(sr.ReadToEnd());

        var preferences = from results in document.Descendants()
                          where results.Name.LocalName == "preference"
                          select new
                          {
                            name = (string) results.Attribute("name"),
                            value = (string) results.Attribute("value")
                          };

        JObject allPreferences = new JObject();

        foreach (var preference in preferences)
        {
          allPreferences[preference.name.ToLowerInvariant()] = preference.value;
        }

        preferencesString = allPreferences.ToString();
      }
    }

    public void getAllPreferences(string options)
    {
      DispatchCommandResult(new PluginResult(PluginResult.Status.OK, preferencesString));
    }
  }
}