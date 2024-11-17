using System;
using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Forms;

namespace Lab8
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void MenuItem_Open_Click(object sender, RoutedEventArgs e)
        {
            var dlg = new System.Windows.Forms.FolderBrowserDialog() { Description = "Select directory to open" };
            if (dlg.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                PopulateTreeView(dlg.SelectedPath);
            }
        }

        private void MenuItem_Exit_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void PopulateTreeView(string directoryPath)
        {
            DirectoryInfo dir = new DirectoryInfo(directoryPath);
            this.treeView.Items.Clear();
            this.treeView.Items.Add(CreateDirectoryNode(dir));
        }

        private TreeViewItem CreateDirectoryNode(DirectoryInfo directoryInfo)
        {
            var directoryNode = new TreeViewItem
            {
                Header = directoryInfo.Name,
                Tag = directoryInfo.FullName
            };

            var contextMenu = new ContextMenu();
            var deleteMenuItem = new MenuItem { Header = "Delete" };
            deleteMenuItem.Click += MenuItem_Delete_Click;
            contextMenu.Items.Add(deleteMenuItem);
            directoryNode.ContextMenu = contextMenu;

            foreach (var directory in directoryInfo.GetDirectories())
            {
                var item = CreateDirectoryNode(directory);
                directoryNode.Items.Add(item);

            }

            foreach (var file in directoryInfo.GetFiles())
            {
                var item = new TreeViewItem
                {
                    Header = file.Name,
                    Tag = file.FullName
                };

                var fileContextMenu = new ContextMenu();
                var fileDeleteMenuItem = new MenuItem { Header = "Delete" };
                fileDeleteMenuItem.Click += MenuItem_Delete_Click;
                fileContextMenu.Items.Add(fileDeleteMenuItem);
                var fileOpenMenuItem = new MenuItem { Header = "Open" };
                fileOpenMenuItem.Click += MenuItem_OpenFile_Click;
                fileContextMenu.Items.Add(fileOpenMenuItem);
                item.ContextMenu = fileContextMenu;

                directoryNode.Items.Add(item);
            }

            return directoryNode;
        }


        private void MenuItem_OpenFile_Click(object sender, RoutedEventArgs e)
        {
            TreeViewItem selectedItem = treeView.SelectedItem as TreeViewItem;
            if (selectedItem != null)
            {
                string path = selectedItem.Tag as string;
                FileAttributes attributes = File.GetAttributes(path);
                if ((attributes & FileAttributes.Directory) != FileAttributes.Directory)
                {
                    textBlock.Text = File.ReadAllText(path);
                }
            }
        }

        private void MenuItem_Delete_Click(object sender, RoutedEventArgs e)
        {
            TreeViewItem selectedItem = treeView.SelectedItem as TreeViewItem;
            if (selectedItem != null)
            {
                string path = selectedItem.Tag as string;
                FileAttributes attributes = File.GetAttributes(path);
                if ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly)
                {
                    attributes = RemoveAttribute(attributes, FileAttributes.ReadOnly);
                    File.SetAttributes(path, attributes);
                }

                if ((attributes & FileAttributes.Directory) == FileAttributes.Directory)
                {
                    Directory.Delete(path, true);
                }
                else
                {
                    File.Delete(path);
                }

                TreeViewItem parentItem = selectedItem.Parent as TreeViewItem;
                if (parentItem != null)
                {
                    parentItem.Items.Remove(selectedItem);
                }
                else
                {
                    treeView.Items.Remove(selectedItem);
                }
            }
        }

        private static FileAttributes RemoveAttribute(FileAttributes attributes, FileAttributes attributesToRemove)
        {
            if ((attributes & attributesToRemove) == attributesToRemove)
            {
                return attributes & ~attributesToRemove;
            }
            return attributes;
        }


    }
}
