"use client"

import { getAuthResponse } from "@/actions/auth/authenticatation"
import { AuthContainer } from "@/components/auth/auth-container"
import { Routes } from "@/constants/routes"
import { ApiResponse, LoginSuccessResponse } from "@/types/auth"
import { NextPageProps } from "@/types/next-page-props"
import { saveAuth, saveOnlyToken, saveTokens } from "@/util/authUtil"
import { notFound, redirect } from "next/navigation"
import { createSearchParamsCache, parseAsString } from "nuqs/server"
import { useEffect } from "react"



const searchParamsCache = createSearchParamsCache( {
    accessToken: parseAsString.withDefault(''),
})



export default  function OAuth2RedirectPage(
    {searchParams}: NextPageProps
): React.JSX.Element {

   useEffect(() => {
       searchParamsCache.parse(searchParams).then(({accessToken}) => {
         saveOnlyToken(accessToken)
        getAuthResponse().then((response: ApiResponse<LoginSuccessResponse>) => {
            const {userData, ...onlyTokenResponse} = response.data
            saveTokens(onlyTokenResponse)
            saveAuth(userData)
            redirect(Routes.Root)
        })
    } ).catch(() => {
        return notFound()
    })

   },[searchParams])

    return <AuthContainer maxWidth="sm">
        <div>Connecting to your account...</div>
    </AuthContainer>
  
}