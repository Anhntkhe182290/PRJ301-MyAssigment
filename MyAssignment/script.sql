USE [master]
GO
/****** Object:  Database [PRJ_ASSIGNMENT]    Script Date: 3/19/2025 4:32:29 PM ******/
CREATE DATABASE [PRJ_ASSIGNMENT]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PRJ_ASSIGNMENT', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.ANHNTK\MSSQL\DATA\PRJ_ASSIGNMENT.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'PRJ_ASSIGNMENT_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.ANHNTK\MSSQL\DATA\PRJ_ASSIGNMENT_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PRJ_ASSIGNMENT].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ARITHABORT OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET  ENABLE_BROKER 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET RECOVERY FULL 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET  MULTI_USER 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'PRJ_ASSIGNMENT', N'ON'
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET QUERY_STORE = ON
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [PRJ_ASSIGNMENT]
GO
/****** Object:  Table [dbo].[AbsentRequest]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AbsentRequest](
	[abid] [nchar](10) NOT NULL,
	[title] [nvarchar](200) NOT NULL,
	[reason] [nvarchar](max) NOT NULL,
	[from] [date] NOT NULL,
	[to] [date] NOT NULL,
	[status] [tinyint] NOT NULL,
	[createBy] [nchar](10) NOT NULL,
	[createAt] [date] NOT NULL,
 CONSTRAINT [PK_AbsentRequest] PRIMARY KEY CLUSTERED 
(
	[abid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Department]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Department](
	[depid] [nchar](10) NOT NULL,
	[depname] [varchar](20) NOT NULL,
 CONSTRAINT [PK_Department] PRIMARY KEY CLUSTERED 
(
	[depid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feature]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feature](
	[fid] [nchar](10) NOT NULL,
	[url] [varchar](max) NOT NULL,
 CONSTRAINT [PK_Feature] PRIMARY KEY CLUSTERED 
(
	[fid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[rid] [nchar](10) NOT NULL,
	[rname] [nchar](10) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[rid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role_Feature]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role_Feature](
	[rid] [nchar](10) NOT NULL,
	[fid] [nchar](10) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[username] [nchar](10) NOT NULL,
	[password] [nchar](10) NOT NULL,
	[FullName] [nvarchar](max) NOT NULL,
	[DOB] [date] NOT NULL,
	[Gender] [tinyint] NOT NULL,
	[depid] [nchar](10) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Role]    Script Date: 3/19/2025 4:32:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Role](
	[username] [nchar](10) NOT NULL,
	[rid] [nchar](10) NOT NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AbsentRequest]  WITH CHECK ADD  CONSTRAINT [FK_AbsentRequest_User] FOREIGN KEY([createBy])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[AbsentRequest] CHECK CONSTRAINT [FK_AbsentRequest_User]
GO
ALTER TABLE [dbo].[Role_Feature]  WITH CHECK ADD  CONSTRAINT [FK_Role_Feature_Feature] FOREIGN KEY([fid])
REFERENCES [dbo].[Feature] ([fid])
GO
ALTER TABLE [dbo].[Role_Feature] CHECK CONSTRAINT [FK_Role_Feature_Feature]
GO
ALTER TABLE [dbo].[Role_Feature]  WITH CHECK ADD  CONSTRAINT [FK_Role_Feature_Role] FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[Role_Feature] CHECK CONSTRAINT [FK_Role_Feature_Role]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [FK_User_Department] FOREIGN KEY([depid])
REFERENCES [dbo].[Department] ([depid])
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [FK_User_Department]
GO
ALTER TABLE [dbo].[User_Role]  WITH CHECK ADD  CONSTRAINT [FK_User_Role_Role] FOREIGN KEY([rid])
REFERENCES [dbo].[Role] ([rid])
GO
ALTER TABLE [dbo].[User_Role] CHECK CONSTRAINT [FK_User_Role_Role]
GO
ALTER TABLE [dbo].[User_Role]  WITH CHECK ADD  CONSTRAINT [FK_User_Role_User] FOREIGN KEY([username])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[User_Role] CHECK CONSTRAINT [FK_User_Role_User]
GO
USE [master]
GO
ALTER DATABASE [PRJ_ASSIGNMENT] SET  READ_WRITE 
GO
