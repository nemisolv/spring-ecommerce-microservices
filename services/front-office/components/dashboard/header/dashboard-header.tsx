"use client"

import { ModeToggle } from "@/components/shared/mode-toggle"

export const DashboardHeader = () => {


    return <div className="h-[46px] bg-slate-300 flex justify-between items-center">
        <h1>Dashboard</h1>
        <ModeToggle/>
    </div>
}