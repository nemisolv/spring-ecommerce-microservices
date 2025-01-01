import { AppSidebar } from "@/components/dashboard/app-sidebar";
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { cookies } from "next/headers";




export default async function layout({children}: {children: React.ReactNode}) {

  const cookieStore = await cookies()
  const defaultOpen = cookieStore.get("sidebar:state")?.value === "true"

    return     <SidebarProvider defaultOpen={defaultOpen}>
        {/* <DashboardHeader/> */}

        <AppSidebar />
        <main className="w-full h-full">
        <SidebarTrigger />
        {children}
      </main>
    </SidebarProvider>
}