//
// Project home: https://github.com/jaxio/celerio-angular-quickstart
//
// Source code generated by Celerio, an Open Source code generator by Jaxio.
// Documentation: http://www.jaxio.com/documentation/celerio/
// Source code: https://github.com/jaxio/celerio/
// Follow us on twitter: @jaxiosoft
// This header can be customized in Celerio conf...
// Template pack-angular:web/src/app/entities/entity-list.component.ts.e.vm
//
import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {Router} from '@angular/router';
import {DataTable, LazyLoadEvent} from 'primeng/primeng';
import {PageResponse} from "../../support/paging";
import {MessageService} from '../../service/message.service';
import {MdDialog} from '@angular/material';
import {AppWidget} from './appWidget';
import {AppWidgetService} from './appWidget.service';
import {App_} from '../app_/app_';

@Component({
    moduleId: module.id,
    templateUrl: 'widget-config.component.html',
    selector: 'widget-config'
})
export class WidgetConfigComponent {

    @Input() header = "AppWidgets...";

    // When 'sub' is true, it means this list is used as a one-to-many list.
    // It belongs to a parent entity, as a result the addNew operation
    // must prefill the parent entity. The prefill is not done here, instead we
    // emit an event.
    // When 'sub' is false, we display basic search criterias
    @Input() sub: boolean;
    @Output() onAddNewClicked = new EventEmitter();

    appWidgetToDelete: AppWidget;

    // basic search criterias (visible if not in 'sub' mode)
    example: AppWidget = new AppWidget();

    // list is paginated
    currentPage: PageResponse<AppWidget> = new PageResponse<AppWidget>(0, 0, []);

    // X to one: input param is used to filter the list when displayed
    // as a one-to-many list by the other side.
    private _app: App_;

    constructor(private router: Router,
                private appWidgetService: AppWidgetService,
                private messageService: MessageService,
                private confirmDeleteDialog: MdDialog) {
    }

    /**
     * When used as a 'sub' component (to display one-to-many list), refreshes the table
     * content when the input changes.
     */
    ngOnChanges(changes: SimpleChanges) {
        this.loadPage({first: 0, rows: 10, sortField: null, sortOrder: null, filters: null, multiSortMeta: null});
    }

    /**
     * Invoked when user presses the search button.
     */
    search(dt: DataTable) {
        if (!this.sub) {
            dt.reset();
            this.loadPage({
                first: 0,
                rows: dt.rows,
                sortField: dt.sortField,
                sortOrder: dt.sortOrder,
                filters: null,
                multiSortMeta: dt.multiSortMeta
            });
        }
    }

    /**
     * Invoked automatically by primeng datatable.
     */
    loadPage(event: LazyLoadEvent) {
        this.appWidgetService.getPage(this.example, event).subscribe(
            pageResponse => this.currentPage = pageResponse,
            error => this.messageService.error('Could not get the results', error)
        );
    }

    // X to one: input param is used to filter the list when displayed
    // as a one-to-many list by the other side.
    @Input()
    set app(app: App_) {
        if (app == null) {
            return;
        }
        this._app = app;

        this.example = new AppWidget();
        this.example.app = new App_();
        this.example.app.id = this._app.id;
    }


    onRowSelect(event: any) {
        let id = event.data.id;
        this.router.navigate(['/appWidget', id]);
    }


    private delete(appWidgetToDelete: AppWidget) {
        let id = appWidgetToDelete.id;

        this.appWidgetService.delete(id).subscribe(
            response => {
                this.currentPage.remove(appWidgetToDelete);
                this.messageService.info('Deleted OK', 'Angular Rocks!');
            },
            error => this.messageService.error('Could not delete!', error)
        );
    }

    saveAndGenApp(appWidgets: AppWidget[]) {
        debugger;
        this.appWidgetService.saveConfigAndGenApp(appWidgets);
    }
}