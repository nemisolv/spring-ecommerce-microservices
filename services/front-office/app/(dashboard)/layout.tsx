import { DashboardHeader } from "@/components/dashboard/header/dashboard-header";


export default function layout({children}: {children: React.ReactNode}) {

    return <div>
        <DashboardHeader/>
        <main>
        {children}
        </main>
    </div>
}