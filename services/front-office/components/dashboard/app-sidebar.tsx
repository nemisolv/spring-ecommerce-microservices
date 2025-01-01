'use client'

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "../ui/dropdown-menu"
import useCurrentUser from "@/hooks/use-current-user"
import { UserAvatar } from "../shared/user-avatar"
import Link from "next/link"
import { Routes } from "@/constants/routes"
import { Logo } from "../ui/logo"
import { NavMain } from "../sidebar/nav-main"
import { Bell, Boxes, ChartBarStacked, ChevronUp,  Home,  PackageSearch,  Settings,  Target, Users } from "lucide-react"

// Menu items.


const data = {
  navMain: [
    {
      title: "Management",
      url: "#",
      icon: Boxes,
      isActive: true,
      items: [
        {
          title: "Brands",
          url: "#",
          icon: Target
        },
         {
          title: "Categories",
          url: "#",
          icon: ChartBarStacked
         },
        {
          title: "Products",
          url: "#",
          icon: PackageSearch
        },

        {
          title: "Inventory",
          url: "#",
          icon: PackageSearch
        },

        {
          title: "Customers",
          url: "#",
          icon: Users
        },
        {
          title: "Users",
          url: "#",
          icon: Users
        },
      ],
    },
  ],
  
 
}

const items = [
  {
    title: "Home",
    url: "/dashboard/management",
    icon: Home,
  },
  {
    title: "Notofication",
    url: "#",
    icon: Bell,
  },
  // {
  //   title: "Calendar",
  //   url: "#",
  //   icon: Calendar,
  // },
  // {
  //   title: "Search",
  //   url: "#",
  //   icon: Search,
  // },
  {
    title: "Settings",
    url: "dashboard/settings",
    icon: Settings,
  },
]

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {

  const {currentUser} = useCurrentUser();

  
  if(!currentUser) return null;
  console.log("🚀 ~ currentUser", currentUser)
  return (
    <Sidebar collapsible="icon" {...props}>
          <SidebarGroupLabel>
          <Link href={Routes.Dashboard

          }>
      <Logo className="justify-center" />
      </Link>


          </SidebarGroupLabel>
          <SidebarContent>
          <NavMain items={data.navMain} />

          <SidebarGroup >
          <SidebarMenu >
              {items.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild >
                    <a href={item.url}>
                      <item.icon />
                      <span>{item.title}</span>
                    </a>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
        </SidebarGroup >
          </SidebarContent>

      {/* sidebar footer */}

      <SidebarFooter>
          <SidebarMenu>
            <SidebarMenuItem>
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <SidebarMenuButton>
                    <UserAvatar className="size-6" src={currentUser.imgUrl} />
                    {/* <User2 />   */}
                    {currentUser.name}
                    <ChevronUp className="ml-auto" />
                  </SidebarMenuButton>
                </DropdownMenuTrigger>
                <DropdownMenuContent
                  side="top"
                  className="w-[--radix-popper-anchor-width]"
                >
                  <DropdownMenuItem>
                    <span>Account</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <span>Billing</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <span>Sign out</span>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarFooter>
    </Sidebar>
  )
}
