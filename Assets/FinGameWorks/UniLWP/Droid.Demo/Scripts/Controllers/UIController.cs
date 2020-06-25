using System;
using FinGameWorks.UniLWP.Droid.Scripts.Managers;
using UnityEngine;
using UnityEngine.UI;

namespace FinGameWorks.UniLWP.Droid.Demo.Scripts.Controllers
{
    /// <summary>
    /// Demo script on UniLWP.Droid callbacks
    /// </summary>
    public class UIController : MonoBehaviour
    {
        public Text OffsetLabel;
        public Text InsetsLabel;
        public Text ScreenLabel;
        public Text ActivityLabel;
        public Text PreviewLabel;
        public Text DarkModeLabel;

#if UNITY_ANDROID
        private void Awake()
        {
            LiveWallpaperManagerDroid.Instance.insetsUpdated += (left, top, right, bottom) =>
            {
                InsetsLabel.text = "Insets Left = " + left + " Top = " + top + " Right = " + right + " Bottom = " + bottom;
            };

            LiveWallpaperManagerDroid.Instance.wallpaperOffsetsUpdated += (xOffset, yOffset, xStep, yStep, simulated) =>
            {
                int xPageCount = (int) Math.Round(1.0 / xStep);
                OffsetLabel.text = "Offset = " + xPageCount * xOffset + " / " + xPageCount + (simulated ? "Simulated" : "Real");
            };

            LiveWallpaperManagerDroid.Instance.screenDisplayStatusUpdated += status =>
            {
                ScreenLabel.text = "Screen = " + status.ToString();
            };
            
            LiveWallpaperManagerDroid.Instance.enterActivityUpdated += status =>
            {
                ActivityLabel.text = "Activity = " + (status ? "In" : "Out");
            };
            
            LiveWallpaperManagerDroid.Instance.serviceIsInPreviewUpdated += status =>
            {
                PreviewLabel.text = "Preview = " + (status ? "In" : "Out");
            };
            
            LiveWallpaperManagerDroid.Instance.darkModeEnableUpdated += status =>
            {
                DarkModeLabel.text = "DarkMode = " + (status ? "On" : "Off");
            };
        }

        public void OpenPreview()
        {
            LiveWallpaperManagerDroid.Instance.OpenPreview();
        }

        public void LaunchActivity()
        {
            LiveWallpaperManagerDroid.Instance.LaunchActivity();
        }
#endif
    }
}