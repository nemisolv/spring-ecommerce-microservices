import { HeadingDesc } from "@/components/dashboard/heading-desc";
import { createTitle } from "@/lib/utils";
import { Metadata } from "next";

import {
    Page,
    PageActions,
    PageBody,
    PageHeader,
    PagePrimaryBar,
    PageSecondaryBar,
    PageTitle
  } from '@/components/ui/page';

  import {
    Tooltip,
    TooltipContent,
    TooltipTrigger
  } from '@/components/ui/tooltip';
import React from "react";
import { InfoIcon } from "lucide-react";

export const metadata: Metadata = {
    title: createTitle("User management"),
}




export default async function UsersManagementPage() {
    const totalCount = 1;
  return (
    // <div>
    //   <HeadingDesc
    //     heading="User management"
    //     desc="Manage your employees and their account permissions here."
    //   />
    // </div>

    <Page>
        <PageHeader>
          <PagePrimaryBar>
            <div className="flex flex-row items-center gap-1">
              <PageTitle>Contacts</PageTitle>
              <Tooltip delayDuration={0}>
                <TooltipTrigger asChild>
                  <InfoIcon className="hidden size-3 shrink-0 text-muted-foreground sm:inline" />
                </TooltipTrigger>
                <TooltipContent>
                  Total {totalCount} {totalCount === 1 ? 'contact' : 'contacts'}{' '}
                  in your organization
                </TooltipContent>
              </Tooltip>
            </div>
            {/* {hasAnyContacts && (
              <PageActions>
                <AddContactButton />
              </PageActions>
            )} */}
          </PagePrimaryBar>
          <PageSecondaryBar>
            <React.Suspense>
              {/* <ContactsFilters tags={tags} /> */}
            </React.Suspense>
          </PageSecondaryBar>
        </PageHeader>
        {/* <PageBody disableScroll={hasAnyContacts}>
          {hasAnyContacts ? (
            <React.Suspense>
              <ContactsDataTable
                data={contacts}
                totalCount={filteredCount}
              />
            </React.Suspense>
          ) : (
            <ContactsEmptyState />
          )}
        </PageBody> */}
      </Page>
  );
}
