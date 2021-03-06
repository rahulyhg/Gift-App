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
import {MatDialog} from '@angular/material';
import {ConfirmDeleteDialogComponent} from "../../support/confirm-delete-dialog.component";
import {WidgetConfig} from './widgetConfig';
import {WidgetConfigService} from './widgetConfig.service';
import {Config_} from '../config_/config_';

@Component({
    moduleId: module.id,
	templateUrl: 'widgetConfig-list.component.html',
	selector: 'widgetConfig-list'
})
export class WidgetConfigListComponent {

    @Input() header = "WidgetConfigs...";

    // When 'sub' is true, it means this list is used as a one-to-many list.
    // It belongs to a parent entity, as a result the addNew operation
    // must prefill the parent entity. The prefill is not done here, instead we
    // emit an event.
    // When 'sub' is false, we display basic search criterias
    @Input() sub : boolean;
    @Output() onAddNewClicked = new EventEmitter();

    widgetConfigToDelete : WidgetConfig;

    // basic search criterias (visible if not in 'sub' mode)
    example : WidgetConfig = new WidgetConfig();

    // list is paginated
    currentPage : PageResponse<WidgetConfig> = new PageResponse<WidgetConfig>(0,0,[]);

    // X to one: input param is used to filter the list when displayed
    // as a one-to-many list by the other side.
    private _config : Config_;

    constructor(private router : Router,
        private widgetConfigService : WidgetConfigService,
        private messageService : MessageService,
        private confirmDeleteDialog: MatDialog) {
    }

    /**
     * When used as a 'sub' component (to display one-to-many list), refreshes the table
     * content when the input changes.
     */
    ngOnChanges(changes: SimpleChanges) {
        this.loadPage({ first: 0, rows: 10, sortField: null, sortOrder: null, filters: null, multiSortMeta: null });
    }

    /**
     * Invoked when user presses the search button.
     */
    search(dt : DataTable) {
        if (!this.sub) {
            dt.reset();
            this.loadPage({ first: 0, rows: dt.rows, sortField: dt.sortField, sortOrder: dt.sortOrder, filters: null, multiSortMeta: dt.multiSortMeta });
        }
    }

    /**
     * Invoked automatically by primeng datatable.
     */
    loadPage(event : LazyLoadEvent) {
        this.widgetConfigService.getPage(this.example, event).
            subscribe(
                pageResponse => this.currentPage = pageResponse,
                error => this.messageService.error('Could not get the results', error)
            );
    }

    // X to one: input param is used to filter the list when displayed
    // as a one-to-many list by the other side.
    @Input()
    set config(config : Config_) {
        if (config == null) {
            return;
        }
        this._config = config;

        this.example = new WidgetConfig();
        this.example.config = new Config_();
        this.example.config.id = this._config.id;
    }


    onRowSelect(event : any) {
        let id =  event.data.id;
        this.router.navigate(['/widgetConfig', id]);
    }

    addNew() {
        if (this.sub) {
            this.onAddNewClicked.emit("addNew");
        } else {
            this.router.navigate(['/widgetConfig', 'new']);
        }
    }

    showDeleteDialog(rowData : any) {
        let widgetConfigToDelete : WidgetConfig = <WidgetConfig> rowData;

        let dialogRef = this.confirmDeleteDialog.open(ConfirmDeleteDialogComponent);
        dialogRef.afterClosed().subscribe(result => {
            if (result === 'delete') {
                this.delete(widgetConfigToDelete);
            }
        });
    }

    private delete(widgetConfigToDelete : WidgetConfig) {
        let id =  widgetConfigToDelete.id;

        this.widgetConfigService.delete(id).
            subscribe(
                response => {
                    this.currentPage.remove(widgetConfigToDelete);
                    this.messageService.info('Deleted OK', 'Angular Rocks!');
                },
                error => this.messageService.error('Could not delete!', error)
            );
    }
}