"use client"

import { ModeToggle } from "@/components/shared/mode-toggle"

export const DashboardHeader = () => {

    const isLogin = false;
    


    return <div className="h-[46px] bg-slate-300 flex justify-between items-center">
        <h1>Dashboard</h1>
        <ModeToggle/>
    </div>
}